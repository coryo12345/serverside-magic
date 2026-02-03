import { AuthToken } from "./authtoken";
import { Result } from "./result";
import type { PlayerSpellResponse, SpellSlot } from "./types";

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
      const headers: Record<string, string> = {};

      const auth = AuthToken.get();
      if (auth && auth.length) {
        headers["Authorization"] = `Bearer ${auth}`;
      }

      const resp = await fetch(url.href, {
        method: options?.method ?? "GET",
        body: options?.body,
        headers,
      });
      if (!resp.ok) {
        const err = await resp.text();
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

  /**
   * @returns the current user's username
   */
  async userinfo(): Promise<Result<string>> {
    const url = new URL("/api/auth/userinfo", window.location.origin);
    return this.request(url, {
      method: "GET",
      responseType: "text",
    });
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

  async authVerify(username: string, code: string): Promise<Result<string>> {
    const url = new URL("/api/auth/validate", window.location.origin);
    const formData = new FormData();
    formData.append("username", username);
    formData.append("code", code.toString());
    return this.request(url, {
      body: formData,
      method: "POST",
      responseType: "text",
    });
  }

  async getMySpells(): Promise<Result<PlayerSpellResponse>> {
    const url = new URL("/api/spells/mine", window.location.origin);
    return this.request(url, { method: "GET", responseType: "json" });
  }

  async setSpellSlot(
    spellId: string,
    slot: number,
  ): Promise<Result<SpellSlot>> {
    const url = new URL("/api/spells/slot", window.location.origin);
    const formData = new FormData();
    formData.append("slot", slot.toString());
    formData.append("spellId", spellId);
    return this.request(url, {
      method: "POST",
      body: formData,
      responseType: "text",
    });
  }

  // get alltrees - gets the skill trees with basic info (id/name/icon/etc...)
  // get mytree/{id} - the full skill tree for ME (what do i have unlocked). Price for each skill, how many skill points, etc..
  // post unlock - unlock a spell for me
  // post resettree - reset a skill tree
}

export const api = new MagicAPI();
