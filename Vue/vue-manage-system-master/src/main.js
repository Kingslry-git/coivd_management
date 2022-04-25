import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import installElementPlus from './plugins/element'
import './assets/css/icon.css'
import './assets/css/font.css';
import request from "./utils/request";
const app = createApp(App)
installElementPlus(app)
app.config.globalProperties.request = request
app
    .use(store)
    .use(router)
    .mount('#app')