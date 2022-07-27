export interface backgroundstepPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
