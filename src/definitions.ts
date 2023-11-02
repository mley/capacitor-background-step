export interface BackgroundstepPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  serviceStart(): Promise<resultInterface>;
  serviceStop(): Promise<resultInterface>;
  getToday(): Promise<StepDataInterface>;
  getStepData(term: { sDateTime: string, eDateTime: string }): Promise<StepDataInterface>;
}

export interface StepDataInterface {
  count: number
}

export interface resultInterface {
  res: boolean
}