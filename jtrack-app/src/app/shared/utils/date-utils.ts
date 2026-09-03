export function formatIsoToLocaleDate(dateString: string | null): string {
  if (!dateString) {
    return '';
  }
  return new Date(dateString).toLocaleDateString('en-AU');
}

export function formatLocaleToIsoDate(dateString: string | null): string {
  if (!dateString) {
    return '';
  }

  const date = new Date(dateString);
  const year = date.getFullYear();
  const month = (date.getMonth() + 1).toString().padStart(2, '0');
  const day = date.getDate().toString().padStart(2, '0');

  return `${year}-${month}-${day}`;
}
