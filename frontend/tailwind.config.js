/** @type {import('tailwindcss').Config} */
module.exports = {
  content: [
    "./src/**/*.{js,jsx,ts,tsx}",
    "./public/index.html",
  ],
  theme: {
    extend: {
      colors: {
        green: {
          50: '#EAFAF2',
          100: '#D4F4E4',
          200: '#AAEAC9',
          300: '#7FDFAF',
          400: '#55D594',
          500: '#2ACA79',
          600: '#23a865',
          700: '#1c8751',
          800: '#15653d',
          900: '#0e4328',
          950: '#0A2E1C'
        }
      }
    },
  },
  plugins: [],
}

