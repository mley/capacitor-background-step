import { WebPlugin } from '@capacitor/core';

import type { BackgroundstepPlugin, StepDataInterface } from './definitions';

export class BackgroundstepWeb extends WebPlugin implements BackgroundstepPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  async getToday(): Promise<StepDataInterface> {
    return {
      count: 0
    };
  };

  async getStepData(options: { sDateTime: string; eDateTime: string; }): Promise<StepDataInterface> {
    console.log('TERM', options);
    return {
      count: 0
    };
  }
}
