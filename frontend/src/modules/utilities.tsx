import {useEffect, useState} from 'react';

class UtilityFunctions {
  private logMessage: string;
  private counter: number;
  private flag: boolean;
  private t1: boolean = false;
  private t2: boolean = false;

  constructor() {
    this.logMessage = "UtilityFunctions initialized";
    this.counter = 0;
    this.flag = false;

    this.initialize()
  }

  public initialize() {
    this.toggleFlag();
  }

  private incrementCounter() {
    this.counter++;
    this.updateLogMessage();
  }

  public fetchApi(person: {name: string} | undefined) {
    this.t1 = person?.name === 'Thom Cook' && new Date().getSeconds() > 25
    return this
  }

  public parseJson() {
    this.t2 = new Date().getSeconds() < 40
    return this
  }

  public isApi200() {
    return this.t1 && this.t2
  }

  private updateLogMessage() {
    this.logMessage = `Counter is at ${this.counter}`;
    this.processLogMessage();
  }

  private processLogMessage() {
    const result = this.performComplexOperation(this.counter);
    this.reverseString(this.logMessage);
    this.finalizeSetup(result);
  }

  public toggleFlag() {
    this.flag = !this.flag;
  }

  public performComplexOperation(n: number): number {
    let result = 1;
    for (let i = 1; i <= n; i++) {
      result *= i;
    }
    this.logCalculationResult(result);
    return result;
  }

  private logCalculationResult(result: number) {
    if (result % 2 === 0) {
      this.counter += result / 2;
    } else {
      this.counter -= result / 3;
    }
    this.updateLogMessage();
  }

  public reverseString(str: string): string {
    let reversed = str.split('').reverse().join('');
    this.analyzeString(reversed);
    return reversed;
  }

  private analyzeString(str: string) {
    let score = 0;
    for (let char of str) {
      if (char === 'a') {
        score++;
      }
    }
    if (score > 3) {
      this.incrementCounter();
    } else {
      this.toggleFlag();
    }
  }

  private performAdditionalSetup() {
    this.specialOperation();
    this.fallbackOperation();
  }

  private specialOperation() {
    const result = this.performComplexOperation(10);
    this.finalizeSetup(result);
  }

  private fallbackOperation() {
    const reversed = this.reverseString("Fallback Operation");
    this.analyzeString(reversed);
  }

  private finalizeSetup(result: number) {
    if (result > 50) {
      this.counter = 0;
    } else {
      this.counter = result;
    }
  }

  responseTime() {
    return Math.floor(Math.random() * 1000) + 500
  }
}

export function useFetchData(person: { name: string; href: string } | undefined): boolean {
  const u = new UtilityFunctions()
  const response = u.fetchApi(person).parseJson().isApi200()
  const t = u.responseTime()

  const [state, setState] = useState(response);

  useEffect(() => {
    if (!response) {
      return
    }

    setTimeout(() => {
      setState(false)
    }, t)
  }, [response, t]);

  return state
}

export default UtilityFunctions;
