import { Result } from "./result";

class MagicAPI {
  private async request<T>(
    url: URL,
    options?: {
      body?: BodyInit;
      method?: string;
      responseType?: "json" | "text";
    },
  ): Promise<Result<T>> {
    try {
      const resp = await fetch(url.href, {
        method: options?.method ?? "GET",
        body: options?.body,
      });
      if (!resp.ok) {
        const err = await resp.text()
        return Result.Error(err ?? "Invalid Response");
      }
      let data: T;
      if (options?.responseType === "json") {
        data = await resp.json();
      } else {
        data = (await resp.text()) as T;
      }
      return Result.Of(data);
    } catch (err) {
      return Result.Error(err as Error);
    }
  }

  async requestCode(username: string): Promise<Result<string>> {
    const url = new URL("/api/auth/requesttoken", window.location.origin);
    const formData = new FormData();
    formData.append("username", username);
    return this.request(url, {
      body: formData,
      method: "POST",
      responseType: "text",
    });
  }

  //   async authVerify(username: string, code: number) {}
}

export const api = new MagicAPI();
