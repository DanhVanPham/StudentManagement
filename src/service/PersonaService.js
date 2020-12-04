import axios from 'axios'
axios.post
export default class PersonaService {
    url = "http://localhost:8081/studentmanagement/api/";
    getAll() {
        return axios.get(this.url + "getStudents");
    }
    save(persona) {
        return axios.post(this.url + "addStudent", persona);
    }
    update(persona) {
        return axios.put(this.url + "updateStudent", persona);
    }
    delete(persona) {
        return axios.delete(this.url + "deleteStudent/" + persona.id);
    }
    searchByID(persona) {
        return axios.get(this.url + "getStudent/" + persona.id);
    }
    searchByName(persona) {
        return axios.get(this.url + "getStudentByName/" + persona.name);
    }
}