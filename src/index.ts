import { registerPlugin } from '@capacitor/core';

import type { backgroundstepPlugin } from './definitions';

const backgroundstep = registerPlugin<backgroundstepPlugin>('backgroundstep', {
  web: () => import('./web').then(m => new m.backgroundstepWeb()),
});

export * from './definitions';
export { backgroundstep };
