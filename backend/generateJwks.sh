#!/bin/bash

# Install jq on Mac or Linux
install_jq() {
    if [[ "$OSTYPE" == "linux-gnu"* ]]; then
        sudo apt-get update && sudo apt-get install -y jq
    elif [[ "$OSTYPE" == "darwin"* ]]; then
        brew install jq
    else
        echo "Unsupported OS. Please install jq manually."
        exit 1
    fi
}

# Check if jq is installed
if ! command -v jq &> /dev/null; then
    echo "jq could not be found, installing..."
    install_jq
fi

# Set the key parameters
KEY_TYPE="RSA"
KEY_ALG="RS256"
KEY_USE="sig"
KEY_SIZE=2048
KEY_ID=$(uuidgen | tr '[:upper:]' '[:lower:]')
CONF_FILE="conf.json"

# Generate the RSA key pair
openssl genpkey -algorithm RSA -out private_key.pem -pkeyopt rsa_keygen_bits:$KEY_SIZE
openssl rsa -in private_key.pem -pubout -out public_key.pem

# Extract components from the private key
N=$(openssl rsa -in private_key.pem -noout -modulus | cut -d '=' -f 2 | xxd -r -p | base64 | tr -d '=' | tr '/+' '_-' | tr -d '\n')
E="AQAB"  # The exponent for RSA keys, typically 65537, base64-url-encoded
D=$(openssl rsa -in private_key.pem -noout -text | awk '/privateExponent:/ {i=1;next} i && /prime1:/ {i=0} i' | tr -d '\n ' | xxd -r -p | base64 | tr -d '=' | tr '/+' '_-' | tr -d '\n')
P=$(openssl rsa -in private_key.pem -noout -text | awk '/prime1:/ {i=1;next} i && /prime2:/ {i=0} i' | tr -d '\n ' | xxd -r -p | base64 | tr -d '=' | tr '/+' '_-' | tr -d '\n')
Q=$(openssl rsa -in private_key.pem -noout -text | awk '/prime2:/ {i=1;next} i && /exponent1:/ {i=0} i' | tr -d '\n ' | xxd -r -p | base64 | tr -d '=' | tr '/+' '_-' | tr -d '\n')
DP=$(openssl rsa -in private_key.pem -noout -text | awk '/exponent1:/ {i=1;next} i && /exponent2:/ {i=0} i' | tr -d '\n ' | xxd -r -p | base64 | tr -d '=' | tr '/+' '_-' | tr -d '\n')
DQ=$(openssl rsa -in private_key.pem -noout -text | awk '/exponent2:/ {i=1;next} i && /coefficient:/ {i=0} i' | tr -d '\n ' | xxd -r -p | base64 | tr -d '=' | tr '/+' '_-' | tr -d '\n')
QI=$(openssl rsa -in private_key.pem -noout -text | awk '/coefficient:/ {i=1;next} i && /privateExponent:/ {i=0} i' | tr -d '\n ' | xxd -r -p | base64 | tr -d '=' | tr '/+' '_-' | tr -d '\n')

# Create the JWK JSON
JWK=$(jq -n --arg kty "$KEY_TYPE" \
             --arg use "$KEY_USE" \
             --arg alg "$KEY_ALG" \
             --arg kid "$KEY_ID" \
             --arg n "$N" \
             --arg e "$E" \
             --arg d "$D" \
             --arg p "$P" \
             --arg q "$Q" \
             --arg dp "$DP" \
             --arg dq "$DQ" \
             --arg qi "$QI" \
             '{ "kty": $kty, "use": $use, "key_ops": ["sign"], "alg": $alg, "kid": $kid, "n": $n, "e": $e, "d": $d, "p": $p, "q": $q, "dp": $dp, "dq": $dq, "qi": $qi }')

# Update or create the conf.json with the new JWKS as an object
if [ -f "$CONF_FILE" ]; then
    jq --argjson jwk "$JWK" '.jwt.jwks = $jwk' $CONF_FILE > tmp.$$.json && mv tmp.$$.json $CONF_FILE
else
    jq -n --argjson jwk "$JWK" '{"jwt": {"jwks": $jwk}}' > $CONF_FILE
fi

# Clean up
rm -f private_key.pem public_key.pem

echo "JWKS updated in $CONF_FILE"
