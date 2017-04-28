export function cloneIt(toClone: any): any {
  return JSON.parse(JSON.stringify(toClone));
}
