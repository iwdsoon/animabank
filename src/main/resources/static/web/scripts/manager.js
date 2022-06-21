Vue.createApp({
    
  data() {
    return {

      URLAPI:"/rest/clients",
      clients:[],
      client:[],
      firstname: "",
      lastname:"",
      emailaddress:"",
      accounts:[],
      account:[],

      }
    },

  created(){

    axios.get (this.URLAPI)
      .then (data => {
        this.clients= data.data._embedded.clients
      })
      .catch(error => console.warn(error.message))
  },

  methods: {

    addClient(){
      if (this.firstname != "" && this.lastname != "" && this.emailaddress != "" && this.emailaddress.includes("@") && this.emailaddress.includes(".")){
        this.client={
            firstName: this.firstname,
            lastName: this.lastname,
            email: this.emailaddress
        }
        axios.post(this.URLAPI, this.client)
          .then((response) => location.reload())
        .catch(error => console.warn(error.message))
      }
    },

    editClient(client){

      let editedClient = {
        firstName:"",
        lastName:"",
        email:"",
      }

      let clientid = client._links.client.href

      Swal.fire({
        title: 'EDIT CLIENT INFORMATION!?',
        text: "Are you sure you want to EDIT this client information?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Edit now!'
      }).then((result) => {

        if (result.isConfirmed) {
          Swal.fire({
            title: 'Edit client',
            icon: 'info',
            html:`
            
            First Name
            <input type="text" class="form-control" id="firstnameEdited" value="${client.firstName}">
            
            
            Last Name
            <input type="text" class="form-control" id="lastnameEdited" value="${client.lastName}">
            
            
            Email Address
            <input type="email" class="form-control" id="emailaddressEdited" value="${client.email}">
            
            `,
            
            showCancelButton: true,
            confirmButtonColor: '#3085d6',
            cancelButtonColor: '#d33',
            confirmButtonText: 'Confirm',
            
            preConfirm: () => {
         
            let firstNameEdited = document.getElementById('firstnameEdited').value;
            let lastNameEdited = document.getElementById('lastnameEdited').value;
            let emailEdited = document.getElementById('emailaddressEdited').value;

            editedClient.firstName = firstNameEdited
            editedClient.lastName = lastNameEdited
            editedClient.email = emailEdited
          
            }
          })
          .then((result) => {
              if (result.isConfirmed) {
                axios.put(clientid, editedClient)
                Swal.fire({
                  title: 'Edited!',
                  text: 'The Client information has beed edited.',
                  icon: 'success',
                  timer:2000
                })
                .then (() => location.reload())
              }
            }).catch(error => console.warn(error.message))
        }
      })
    },


    deleteClient(client){

      let clientid = client._links.client.href

      Swal.fire({
        title: 'DELETE CLIENT!?',
        text: "Are you sure you want to DELETE THIS CLIENT?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Delete Now!'
      }).then ((result) => {
            if (result.isConfirmed) {
              axios.delete(clientid)
              Swal.fire({
                title: 'Deleted!',
                text: 'The Client has been deleted.',
                icon: 'success',
                timer:2000
              })
              .then (() => location.reload())
            } 
        }).catch(error => console.warn(error.message))
    },

  },


  computed: {

    
  },
  
}).mount('#app')