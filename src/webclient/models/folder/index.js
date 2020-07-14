/**
 * Value object that contains information about {@link FolderItem}.
 */
export default class FolderModel {

  _id;
  _parentId;
  _name;

  /**
   * Returns instance of {@link FolderModel} with set parameters.
   * @param {string} id folder identifier.
   * @param {string} parentId parent folder identifier.
   * @param {string} name folder name.
   */
  constructor(id, parentId, name) {
    this._id = id;
    this._parentId = parentId;
    this._name = name;
  }

  /**
   * Returns folder identifier.
   * @return {string} folder identifier.
   */
  get id() {
    return this._id;
  }

  /**
   * Returns parent folder identifier.
   * @return {string} folder identifier.
   */
  get parentId() {
    return this._parentId;
  }

  /**
   * Returns folder name.
   * @return {string} folder name.
   */
  get name() {
    return this._name;
  }
}
