<template>
  <div style="margin: 0 auto; width: 80%">
    <Toast />
    <Panel header="Student Management">
      <Menubar :model="items" />
      <br />
      <DataTable
        :value="personas"
        :paginator="true"
        :rows="5"
        selectionMode="single"
        :selection.sync="persona"
        datakey="id"
        ref="datatable"
      >
        <Column field="id" header="ID"></Column>
        <Column field="name" header="Name"></Column>
        <Column field="email" header="Email"></Column>
        <Column field="address" header="Address"></Column>
        <Column
          header="Action"
          headerStyle="width: 8rem; text-align: center"
          bodyStyle="text-align: center; overflow: visible"
        >
          <template #body>
            <Button
              type="button"
              icon="pi pi-fw pi-pencil"
              @click="showEditModal"
            ></Button>
            <Button
              type="button"
              icon="pi pi-fw pi-trash"
              @click="deleteStudent"
            ></Button>
          </template>
        </Column>
      </DataTable>
    </Panel>
    <Dialog header="Create Student" :visible.sync="displayModal" :modal="true">
      <span class="p-float-label">
        <InputNumber
          id="id"
          v-model.trim="persona.id"
          style="100%"
          mode="decimal"
          :useGrouping="false"
          @keypress="checkInputUserID"
        />
        <label for="id">ID</label> </span
      ><br />
      <span class="p-float-label">
        <InputText
          id="username"
          type="text"
          v-model.trim="persona.name"
          @keypress="checkInputUsername"
          style="100%"
        />
        <label for="username">Username</label>
      </span>
      <br />
      <span class="p-float-label">
        <InputText
          id="email"
          type="text"
          v-model.trim="persona.email"
          @keypress="checkInputEmail"
          style="100%"
        />
        <label for="email">Email</label> </span
      ><br />
      <span class="p-float-label">
        <InputText
          id="address"
          type="text"
          @keypress="checkInputUserAddress"
          v-model.trim="persona.address"
          style="100%"
        />
        <label for="address">Address</label> </span
      ><br />
      <template #footer>
        <Button label="Accept" icon="pi pi-check" @click="save" />
        <Button
          label="Cancel"
          icon="pi pi-times"
          @click="closeModal"
          class="p-button-secondary"
        />
      </template>
    </Dialog>
    <Dialog
      header="Delete Student"
      :visible.sync="displayModalDel"
      :modal="true"
    >
      <span class="p-float-label">
        <InputNumber
          id="id"
          v-model.trim="persona.id"
          style="100%"
          mode="decimal"
          :useGrouping="false"
        />
        <label for="id">ID</label> </span
      ><br />
      <template #footer>
        <Button label="Accept" icon="pi pi-check" @click="deleteStudent" />
        <Button
          label="Cancel"
          icon="pi pi-times"
          @click="closeModal"
          class="p-button-secondary"
        />
      </template>
    </Dialog>
    <Dialog
      header="Edit Student"
      :visible.sync="displayModalEdit"
      :modal="true"
    >
      <span class="p-float-label">
        <InputText
          id="id"
          type="text"
          v-model.trim.lazy="persona.id"
          style="100%"
          disabled
        />
        <label for="id">ID</label> </span
      ><br />
      <span class="p-float-label">
        <InputText
          id="username"
          type="text"
          v-model.trim.lazy="persona.name"
          @keypress="checkInputUsername"
          style="100%"
        />
        <label for="username">Username</label> </span
      ><br />
      <span class="p-float-label">
        <InputText
          id="email"
          type="text"
          v-model.trim.lazy="persona.email"
          @keypress="checkInputEmail"
          style="100%"
        />
        <label for="email">Email</label> </span
      ><br />
      <span class="p-float-label">
        <InputText
          id="address"
          type="text"
          v-model.trim.lazy="persona.address"
          @keypress="checkInputUserAddress"
          style="100%"
        />
        <label for="address">Address</label> </span
      ><br />
      <template #footer>
        <Button label="Accept" icon="pi pi-check" @click="updateStudent" />
        <Button
          label="Cancel"
          icon="pi pi-times"
          @click="closeModal"
          class="p-button-secondary"
        />
      </template>
    </Dialog>

    <Dialog
      header="Search Student By ID"
      :visible.sync="displayModalSearchID"
      :modal="true"
    >
      <span class="p-float-label">
        <InputText id="id" type="text" v-model="persona.id" style="100%" />
        <label for="id">ID</label> </span
      ><br />
      <template #footer>
        <Button label="Accept" icon="pi pi-check" @click="searchByID" />
        <Button
          label="Cancel"
          icon="pi pi-times"
          @click="closeModalSearch"
          class="p-button-secondary"
        />
      </template>
    </Dialog>

    <Dialog
      header="Search Student By Name"
      :visible.sync="displayModalSearchName"
      :modal="true"
    >
      <span class="p-float-label">
        <InputText id="name" type="text" v-model="persona.name" style="100%" />
        <label for="name">Name</label> </span
      ><br />
      <template #footer>
        <Button label="Accept" icon="pi pi-check" @click="searchByName" />
        <Button
          label="Cancel"
          icon="pi pi-times"
          @click="closeModalSearch"
          class="p-button-secondary"
        />
      </template>
    </Dialog>
  </div>
</template>

<script>
import PersonaService from "../service/PersonaService";
export default {
  name: "CrudApp",
  data() {
    return {
      personas: null,
      persona: {
        id: null,
        name: null,
        email: null,
        address: null,
      },
      items: [
        {
          label: "New",
          icon: "pi pi-fw pi-plus",
          command: () => {
            (this.persona = {
              id: null,
              name: null,
              email: null,
              address: null,
            }),
              this.showSaveModel();
          },
        },
        {
          label: "Edit",
          icon: "pi pi-fw pi-pencil",
          command: () => {
            if (this.persona.id != null) {
              this.showEditModal();
            }
          },
        },
        {
          label: "Refresh",
          icon: "pi pi-fw pi-refresh",
          command: () => {
            this.getAll();
            this.displayModal = false;
            this.displayModalDel = false;
            this.displayModalEdit = false;
            this.displayModalSearchID = false;
            this.displayModalSearchName = false;
            this.persona = {
              id: null,
              name: null,
              email: null,
              address: null,
            };
          },
        },
        {
          label: "Delete",
          icon: "pi pi-fw pi-trash",
          command: () => {
            if (this.persona.id == null || this.persona.id == "") {
              this.showDeleteModal();
            } else {
              this.deleteStudent();
            }
          },
        },
        {
          label: "SearchByID",
          icon: "pi pi-fw pi-search",
          command: () => {
            this.showSearchIDModal();
          },
        },
        {
          label: "SearchByName",
          icon: "pi pi-fw pi-search",
          command: () => {
            this.showSearchNameModal();
          },
        },
      ],
      displayModal: false,
      displayModalDel: false,
      displayModalEdit: false,
      displayModalSearchID: false,
      displayModalSearchName: false,
      inputName: false,
      inputAddress: false,
      inputEmail: false,
    };
  },
  personaService: null,
  created() {
    this.personaService = new PersonaService();
  },
  mounted() {
    this.getAll();
  },
  methods: {
    showSaveModel() {
      this.displayModal = true;
    },
    showDeleteModal() {
      this.displayModalDel = true;
    },
    showEditModal() {
      this.displayModalEdit = true;
    },
    showSearchIDModal() {
      this.displayModalSearchID = true;
      this.persona = {
        id: null,
        name: null,
        email: null,
        address: null,
      };
    },
    showSearchNameModal() {
      this.displayModalSearchName = true;
      this.persona = {
        id: null,
        name: null,
        email: null,
        address: null,
      };
    },
    checkInputUsername() {
      if (this.persona.name.length > 50) {
        alert("Required input length of username smaller 51");
        this.inputName = false;
        event.preventDefault();
      } else if (this.persona.name.length <= 0) {
        alert("Required input length of username greater than 0");
        this.inputName = false;
      } else {
        this.inputName = true;
      }
    },
    checkInputEmail() {
      if (this.persona.email.length > 30) {
        alert("Required input length of email smaller 31");
        event.preventDefault();
        this.inputEmail = false;
      } else if (this.persona.email.length <= 0) {
        alert("Required input length of email greater than 0");
        this.inputEmail = false;
      } else if (!this.persona.email.match("^[a-zA-Z0-9]*?@fpt.edu\\.vn")) {
        alert(
          "Email is invalid. Required input email fpt edu! Enter email with format(abc@fpt.edu.vn)"
        );
        this.inputEmail = false;
      } else {
        this.inputEmail = true;
      }
    },
    checkInputUserAddress() {
      if (this.persona.address.length > 30) {
        alert("Required input length of address smaller 31");
        event.preventDefault();
        this.inputAddress = false;
      } else if (this.persona.address.length <= 0) {
        alert("Required input length of address greater than 0");
        this.inputAddress = false;
      } else {
        this.inputAddress = true;
      }
    },
    getAll() {
      this.personaService.getAll().then((data) => {
        this.personas = data.data;
        console.log(this.personas);
      });
    },
    save() {
      this.checkInputUsername();
      this.checkInputEmail();
      this.checkInputUserAddress();
      if (this.inputName && this.inputEmail && this.inputAddress) {
        if (confirm("Do you want to save new student?")) {
          this.personaService.save(this.persona).then((data) => {
            console.log(data.data);
            if (data.status === 200) {
              if (data.data != null) {
                this.displayModal = false;
                this.persona = {
                  id: null,
                  name: null,
                  email: null,
                  address: null,
                };
                this.getAll();
                this.$toast.add({
                  severity: "success",
                  summary: "Save successful",
                  detail: "Message Content",
                  life: 3000,
                });
              } else {
                this.$toast.add({
                  severity: "error",
                  summary: "Save error!",
                  detail: "Message Content",
                  life: 3000,
                });
              }
            } else {
              this.$toast.add({
                severity: "error",
                summary: "Save error!",
                detail: "Message Content",
                life: 3000,
              });
            }
          });
        }
      } else {
        this.$toast.add({
          severity: "error",
          summary: "Something is wrong. Required check input again!",
          detail: "Message Content",
          life: 3000,
        });
      }
    },
    searchByID() {
      if (this.persona.id == null || this.persona.id == "") {
        this.personaService.getAll().then((data) => {
          if (data.status === 200) {
            this.personas = data.data;
            console.log(this.personas);
            this.displayModalSearchID = false;
            this.$toast.add({
              severity: "success",
              summary: "Search By Id successful",
              detail: "Message Content",
              life: 3000,
            });
          } else {
            this.$toast.add({
              severity: "error",
              summary: "Search By Id error!",
              detail: "Message Content",
              life: 3000,
            });
          }
          this.persona = {
            id: null,
            name: null,
            email: null,
            address: null,
          };
        });
      } else {
        this.personaService.searchByID(this.persona).then((data) => {
          if (data.status === 200) {
            this.personas = data.data;
            this.$toast.add({
              severity: "success",
              summary: "Search By Id successful",
              detail: "Message Content",
              life: 3000,
            });
            this.displayModalSearchID = false;
          } else {
            this.$toast.add({
              severity: "error",
              summary: "Search By Id error!",
              detail: "Message Content",
              life: 3000,
            });
          }
          this.persona = {
            id: null,
            name: null,
            email: null,
            address: null,
          };
        });
      }
    },

    searchByName() {
      if (this.persona.name == null || this.persona.name == "") {
        this.personaService.getAll().then((data) => {
          if (data.status === 200) {
            this.personas = data.data;
            console.log(this.personas);
            this.displayModalSearchName = false;
            this.$toast.add({
              severity: "success",
              summary: "Search By Name successful",
              detail: "Message Content",
              life: 3000,
            });
          } else {
            this.$toast.add({
              severity: "error",
              summary: "Search By Name error!",
              detail: "Message Content",
              life: 3000,
            });
          }
          this.persona = {
            id: null,
            name: null,
            email: null,
            address: null,
          };
        });
      } else {
        this.personaService.searchByName(this.persona).then((data) => {
          if (data.status === 200) {
            this.personas = data.data;
            this.$toast.add({
              severity: "success",
              summary: "Search By Name successful",
              detail: "Message Content",
              life: 3000,
            });
            this.displayModalSearchName = false;
          } else {
            this.$toast.add({
              severity: "error",
              summary: "Search By Name error!",
              detail: "Message Content",
              life: 3000,
            });
          }
          this.persona = {
            id: null,
            name: null,
            email: null,
            address: null,
          };
        });
      }
    },

    updateStudent() {
      this.checkInputUsername();
      this.checkInputEmail();
      this.checkInputUserAddress();
      if (this.inputName && this.inputEmail && this.inputAddress) {
        if (confirm("Do you want to update student existed?")) {
          this.personaService.update(this.persona).then((data) => {
            if (data.status === 200) {
              this.displayModalEdit = false;
              this.persona = {
                id: null,
                name: null,
                email: null,
                address: null,
              };
              this.getAll();
              this.$toast.add({
                severity: "success",
                summary: "Update Student successful",
                detail: "Message Content",
                life: 3000,
              });
            } else {
              this.$toast.add({
                severity: "error",
                summary: "Update Student error!",
                detail: "Message Content",
                life: 3000,
              });
            }
          });
        }
      } else {
        this.$toast.add({
          severity: "error",
          summary: "Something is wrong. Required check input again!",
          detail: "Message Content",
          life: 3000,
        });
      }
    },
    deleteStudent() {
      if (this.persona.id == null || this.persona.id == "") {
        this.$toast.add({
          severity: "error",
          summary: "Something is wrong. Required check input again!",
          detail: "Message Content",
          life: 3000,
        });
      } else {
        if (confirm("Do you want to delete student existed?")) {
          this.personaService.delete(this.persona).then((data) => {
            if (data.status === 200) {
              this.displayModalDel = false;
              this.persona = {
                id: null,
                name: null,
                email: null,
                address: null,
              };
              this.$toast.add({
                severity: "success",
                summary: "Delete Student successful",
                detail: "Message Content",
                life: 3000,
              });
              this.getAll();
            } else {
              this.$toast.add({
                severity: "error",
                summary: "Delete Student error!",
                detail: "Message Content",
                life: 3000,
              });
            }
          });
        }
      }
    },
    closeModal() {
      this.displayModal = false;
      this.displayModalDel = false;
      this.displayModalEdit = false;
      this.displayModalSearchID = false;
      this.displayModalSearchName = false;
      this.persona = {
        id: null,
        name: null,
        email: null,
        address: null,
      };
      this.getAll();
    },
    closeModalSearch() {
      this.displayModal = false;
      this.displayModalDel = false;
      this.displayModalEdit = false;
      this.displayModalSearchID = false;
      this.displayModalSearchName = false;
      this.persona = {
        id: null,
        name: null,
        email: null,
        address: null,
      };
    },
  },
};
</script>

<style >
</style>