export interface BackgroundstepPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
