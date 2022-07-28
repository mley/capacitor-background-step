export interface BackgroundstepPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  getToday(): Promise<StepDataInterface>;
}

export interface StepDataInterface {
  count: number
}