export class Result<T> {
  private data: T | null;
  private err?: Error | null;

  private constructor(data: T | null, error?: Error | null) {
    this.data = data;
    this.err = error;
  }

  static Of<T>(data: T) {
    return new Result<T>(data, null);
  }

  static Error(error: Error | string) {
    if (typeof error === "string") {
      return new Result<any>(null, new Error(error));
    }
    return new Result<any>(null, error);
  }

  isValid(): boolean {
    return !this.err;
  }

  isError(): boolean {
    return !!this.err;
  }

  get(): T {
    return this.data as T;
  }

  error(): Error {
    return this.err as Error;
  }
}
