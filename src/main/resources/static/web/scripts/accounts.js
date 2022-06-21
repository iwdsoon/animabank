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
          errorActive: false,
          errorMessage: null,
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
        axios.post('/api/clients/current/accounts')
        .then(() => location.reload())
        .catch(error =>{
          this.errorActive = true
          this.errorMessage = (error.response.data)})
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
        header.classList.toggle('body-pd')
        }

    },
  

    computed: { 

    },
    
  }).mount('#app')