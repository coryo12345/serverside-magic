import { jwtDecode } from "jwt-decode";

const storageKey: string = "server-magic-token";

export class AuthToken {
  static get(): string {
    return localStorage.getItem(storageKey) ?? "";
  }

  static set(val: string) {
    localStorage.setItem(storageKey, val);
  }

  static clear() {
    localStorage.setItem(storageKey, "");
  }

  static isExpired(token: string): boolean {
    const { exp } = jwtDecode(token);
    const currentTime = new Date().getTime() / 1000;
    return !exp || currentTime > exp;
  }
}
