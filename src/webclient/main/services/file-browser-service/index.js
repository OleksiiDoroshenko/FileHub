/**
 * Provide function foe selecting file from user's device.
 */
export default class FileBrowserService {

  /**
   * Returns selected file from file browser.
   * @returns {Promise<File>}
   */
  async selectFile() {
    const input = document.createElement('input');
    input.setAttribute('type', 'file');
    input.click();
    input.addEventListener('change', () => {
      return input.files[0];
    });
  };
}
