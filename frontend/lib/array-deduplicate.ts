export const dedpulicate = <T>(array: T[]): T[] => {
    return array.filter((item, pos, arr) => {
        return pos === 0 || item !== arr[pos-1];
    });
}
