export const SPELL_SLOTS = {
  RRR: parseInt("0000", 2),
  RLR: parseInt("0100", 2),
  RRL: parseInt("1000", 2),
  RLL: parseInt("1100", 2),
  SRRR: parseInt("0001", 2),
  SRLR: parseInt("0101", 2),
  SRRL: parseInt("1001", 2),
  SRLL: parseInt("1101", 2),
} as const;

export function slotNumberToString(slotNumber: number | string): string {
  let val = parseInt(slotNumber.toString());
  // See `toPaddedBinaryString` in PlayerCastStatus.java
  const prepend = Math.pow(2, 4);
  const mask = prepend - 1;
  val = val & mask;
  val = val | prepend;
  const finalBinaryVal = val.toString(2).substring(1);
  // now we need to flip around and convert to human-readable
  const items: String[] = [];
  for (let i = finalBinaryVal.length - 1; i >= 0; i--) {
    const char = finalBinaryVal.charAt(i);
    if (i == finalBinaryVal.length - 1) {
      if (char == "1") {
        items.push("Shift");
      }
    } else {
      items.push(char == "1" ? "L" : "R");
    }
  }
  return items.join("-");
}
