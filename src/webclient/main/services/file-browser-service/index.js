export default class FileBrowserService {
  selectFile() {
    return new Promise((resolve => {
      const input = document.createElement('input');
      input.setAttribute('type', 'file');
      input.click();
      input.addEventListener('change', () => {
        resolve(input.files[0]);
      });
    }));
  }
}
