export default class DownloadFileService {
  /**
   * Downloads file.
   * @param {Blob} file - file for downloading.
   * @param {string} fileName - file name.
   */
  download(file, fileName) {
    const anchor = document.createElement('a');
    anchor.setAttribute('download', fileName);
    const url = URL.createObjectURL(file);
    anchor.setAttribute('href', url);
    anchor.click();
  }
}
