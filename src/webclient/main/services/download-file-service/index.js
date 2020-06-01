export default class DownloadFileService {

  /**
   * Downloads file.
   * @param {Blob} file - file for downloading.
   */
  download(file) {
    const anchor = document.createElement('a');
    anchor.setAttribute('download', file.name);
    const url = URL.createObjectURL(file);
    anchor.setAttribute('href', url);
    anchor.click();
  }
}
