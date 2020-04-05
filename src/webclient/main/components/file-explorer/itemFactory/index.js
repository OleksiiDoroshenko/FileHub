import FolderItem from '../folder-item';
import FileItem from '../file-item';

/**
 * @typedef FileConfig.
 * @param {string} name - file name with type.
 * @param {string} mimeType - files mimeType.
 * @param {string} size - file size;
 */

/**
 * @typedef FolderConfig.
 * @param {string} name - folder name.
 * @param {string} itemsAmount - number of elements in the folder.
 */

/**
 * Returns instance of {@link FileItem} or {@link FolderItem}.
 * @param {string} type - item type, folder or file.
 * @param {HTMLElement} container - container for element rendering.
 * @param {FileConfig|FolderConfig} config - configuration for render item.
 * @return {FileItem|FolderItem}
 */
export default function createItem(type, container, config) {
  switch (type) {
    case 'folder': {
      return new FolderItem(container, config);
    }
    case 'file': {
      config['fileIcon'] = _getFileIcon(config.mimeType);
      return new FileItem(container, config);
    }
  }
}

/**
 * Returns file icon by its mimeType.
 * @param {string} mimeType - files mimeType.
 * @return {string} bootstrap icon class.
 * @private
 */
function _getFileIcon(mimeType) {
  const icons = {
    img: 'glyphicon-picture',
    text: 'glyphicon-book',
    video: 'glyphicon-film',
    audio: 'glyphicon-music',
  };
  return icons[mimeType];
}
