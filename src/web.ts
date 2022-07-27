import { WebPlugin } from '@capacitor/core';

import type { backgroundstepPlugin } from './definitions';

export class backgroundstepWeb extends WebPlugin implements backgroundstepPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
