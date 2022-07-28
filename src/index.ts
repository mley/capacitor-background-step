import { registerPlugin } from '@capacitor/core';

import type { BackgroundstepPlugin } from './definitions';

const backgroundstep = registerPlugin<BackgroundstepPlugin>('backgroundstep', {
  web: () => import('./web').then(m => new m.BackgroundstepWeb()),
});

export * from './definitions';
export { backgroundstep };
