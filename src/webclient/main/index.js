import Application from './app.js';

const root = document.querySelector('body');

new Application(root, {});
window.location.hash = '#/';
