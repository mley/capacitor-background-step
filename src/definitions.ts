export interface BackgroundstepPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  getToday(): Promise<StepDataInterface>;
  getStepData(term: { sDateTime: string, eDateTime: string }): Promise<StepDataInterface>;
}

export interface StepDataInterface {
  count: number
}