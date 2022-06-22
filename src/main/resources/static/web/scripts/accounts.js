Vue.createApp({
    
    data() {
      return {
          URLAPI: "/api/clients/current",
          URLLASTTRANSACTIONS: "/api/clients/current/accounts/transactions/latest",
          client:[],
          intials:"",
          accounts:[],
          account:[],
          lastTransactions:[],
          debitClass: 'table-danger',
          creditClass: 'table-success',

        }
      },
  
    created(){
        axios.get(this.URLAPI)
        .then (data => {
            this.client = data.data
            this.accounts = data.data.accounts.sort((a,b) => a.id - b.id)
            this.loans = data.data.loans.sort((a,b) => a.id - b.id)
            let firstNameLetter = this.client.firstName.charAt(0).toUpperCase()
            let secondNameLetter = this.client.lastName.charAt(0).toUpperCase()
            this.intials =  firstNameLetter + secondNameLetter
        })
        .catch(error => console.warn(error.message));

        axios.get(this.URLLASTTRANSACTIONS)
        .then (data => {
          this.lastTransactions = data.data
        })

    },
  
    methods: {
      logOut(){
        axios.post('/api/logout')
        .then(() => window.location.replace("/web/login.html"))
        .catch(error => console.log(error))
      },


      createAccount(){
        Swal.fire({
          title: 'Do you want to create a new account?',
          html: `<select id="selectAccountType" class="mt-3 form-select">
          <option value="SAVING">Savings account</option>
          <option value="CURRENT">Current account</option>
        </select>` ,
        showCancelButton: true,
        confirmButtonColor: '#014377',
        cancelButtonColor: '#ff0000',
        confirmButtonText: 'Confirm'
      }).then((result) => {
          if (result.isConfirmed) {

              let accountType = document.getElementById("selectAccountType")

              axios.post('/api/clients/current/accounts', `type=${accountType.value}`)
              .then(() => {
                Swal.fire({
                  title: 'Succeed',
                  text: "Account created successful",
                  icon: 'success',
                  timer:2000
                }).then (() => location.reload())
              }).catch ((error) => {
                Swal.fire({
                title: 'Error',
                text: error.response.data,
                icon: 'error',
                timer:2000
              })})
            }
          })
        },

      disableAccount(id){
        Swal.fire({
          title: 'Disable Account',
          text: "Are you sure you want to disable this account?",
          icon: 'warning',
          showCancelButton: true,
          confirmButtonColor: '#014377',
          cancelButtonColor: '#ff0000',
          confirmButtonText: 'Confirm'
        }).then ((result) => {
              if (result.isConfirmed) {
                Swal.fire({
                  input: "password",
                  inputLabel: "Enter your password to confirm",
                  inputValue:"",
                  confirmButtonColor: '#014377',
                  cancelButtonColor: '#ff0000',
                  showCancelButton: true,
                  confirmButtonText: 'Disable now'
                })
                .then(result =>{
                  if (result.isConfirmed){
                    axios.patch('/api/clients/current/accounts', `id=${id}&password=${result.value}`)
                    .then(() => {
                      Swal.fire({
                        title: 'Succeed',
                        text: "Account disabled successful",
                        icon: 'success',
                        timer:2000
                      }).then (() => location.reload())
                    }).catch ((error) => {
                      Swal.fire({
                      title: 'Error',
                      text: error.response.data,
                      icon: 'error',
                      timer:2000
                    })})
                  }
                })
              }
            })
        },

        toggleNav() {

          const toggle = document.getElementById("header-toggle"),
          nav = document.getElementById("nav-bar"),
          body = document.getElementById("body"),
          header = document.getElementById("header")
          
          // show navbar
          nav.classList.toggle('show')
          // change icon
          toggle.classList.toggle('bx-x')
          // add padding to body
          body.classList.toggle('body-pd')
          // add padding to header
          header.classList.toggle('header-pd')
          }

    },
  

    computed: { 

    },
    
  }).mount('#app')