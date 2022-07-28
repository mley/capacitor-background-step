export interface BackgroundstepPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
  getToday(): Promise<{ date: string, total: number }>;
}
