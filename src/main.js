import Vue from 'vue'
import App from './App.vue'
import DataTable from 'primevue/datatable'
import Column from 'primevue/column'
import Panel from 'primevue/panel'
import Dialog from 'primevue/dialog'
import InputText from 'primevue/inputtext'
import Menubar from 'primevue/menubar'
import Button from 'primevue/button'
import InputNumber from 'primevue/inputnumber';
import ToastService from 'primevue/toastservice';
import Toast from 'primevue/toast';

import 'primevue/resources/themes/saga-blue/theme.css';       //theme
import 'primevue/resources/primevue.min.css';                 //core css
import 'primeicons/primeicons.css';                           //icons

// import {createApp} from 'vue';
// import ToastService from 'primevue/toastservice';
// import Toast from 'primevue/toast';



// const app = createApp(App);
// app.use(ToastService);


Vue.config.productionTip = false
Vue.component('DataTable', DataTable);
Vue.component('Column', Column);
Vue.component('Panel', Panel);
Vue.component('Dialog', Dialog);
Vue.component('InputText', InputText);
Vue.component('Menubar', Menubar);
Vue.component('Button', Button);
Vue.component('InputNumber', InputNumber);
Vue.component('Toast', Toast);
Vue.use(ToastService);
new Vue({
  render: h => h(App),
}).$mount('#app')
