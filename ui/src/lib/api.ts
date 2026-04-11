import { AuthToken } from "./authtoken";
import { Result } from "./result";
import type {
  PlayerSpellResponse,
  SecretSkill,
  SkillTree,
  SpellConfigResponse,
  SpellSlot,
} from "./types";

class MagicAPI {
  private async request<T>(
    url: URL,
    options?: {
      body?: BodyInit;
      method?: string;
      responseType?: "json" | "text";
      headers?: Record<string, string>;
    },
  ): Promise<Result<T>> {
    try {
      const headers: Record<string, string> = {};

      const auth = AuthToken.get();
      if (auth && auth.length) {
        headers["Authorization"] = `Bearer ${auth}`;
      }

      if (options?.headers) {
        Object.assign(headers, options.headers);
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

  async clearSpellSlot(slot: number): Promise<Result<void>> {
    const url = new URL("/api/spells/slot", window.location.origin);
    url.searchParams.append("slot", slot.toString());
    return this.request(url, {
      method: "DELETE",
      responseType: "text",
    });
  }

  async getSkillTrees(): Promise<Result<SkillTree[]>> {
    const url = new URL("/api/skills/tree", window.location.origin);
    return this.request(url, { method: "GET", responseType: "json" });
  }

  async getSpellConfig(spellId: string): Promise<Result<SpellConfigResponse>> {
    const url = new URL(`/api/spells/config/${spellId}`, window.location.origin);
    return this.request(url, { method: "GET", responseType: "json" });
  }

  async setSpellConfig(
    spellId: string,
    config: Record<string, unknown>,
  ): Promise<Result<void>> {
    const url = new URL(`/api/spells/config/${spellId}`, window.location.origin);
    return this.request(url, {
      method: "POST",
      body: JSON.stringify(config),
      responseType: "text",
      headers: { "Content-Type": "application/json" },
    });
  }

  async getMySecrets(): Promise<Result<SecretSkill[]>> {
    const url = new URL("/api/skills/secrets", window.location.origin);
    return this.request(url, { method: "GET", responseType: "json" });
  }
}

export const api = new MagicAPI();
