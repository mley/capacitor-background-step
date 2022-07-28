import { WebPlugin } from '@capacitor/core';

import type { BackgroundstepPlugin } from './definitions';

export class BackgroundstepWeb extends WebPlugin implements BackgroundstepPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
