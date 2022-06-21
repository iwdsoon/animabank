Vue.createApp({
    
    data() {
      return {
          URLAPI: "/api/clients/current",
          client:[],
          intials:"",
          accounts:[],
          account:[],
          amount:0,
          description:"",
          ownNumberAccount:"",
          numberDestiny:"",
          ownOrThirdAccount:"",
        }
      },
  
    created(){
        axios.get(this.URLAPI)
        .then (data => {
            this.client = data.data
            this.accounts = data.data.accounts.sort((a,b) => a.id - b.id);
            let firstNameLetter = this.client.firstName.charAt(0).toUpperCase()
            let secondNameLetter = this.client.lastName.charAt(0).toUpperCase()
            this.intials =  firstNameLetter + secondNameLetter
        })
        .catch(error => console.warn(error.message))

    },
  
    methods: {

      logOut(){
        axios.post('/api/logout')
        .then(() => window.location.replace("/web/login.html"))
        .catch(error => console.log(error))
        },


      accountsLeft(){
        return this.accounts.filter(account => account.number != this.ownNumberAccount)
      },


      makeATransfer(){
        Swal.fire({
          title: 'Transfer',
          text: "Are you sure you want to make this transfer",
          icon: 'warning',
          showCancelButton: true,
          confirmButtonColor: '#014377',
          cancelButtonColor: '#ff0000',
          confirmButtonText: 'Transfer now'
        }).then ((result) => {
              if (result.isConfirmed) {
                axios.post('/api/transactions',`amount=${this.amount}&description=${this.description}&ownNumberAccount=${this.ownNumberAccount}&numberDestiny=${this.numberDestiny}`,
                {headers:
                {'content-type':'application/x-www-form-urlencoded'}}
                ).then(() => {
                  Swal.fire({
                    title: 'Succeed',
                    text: "Transaction successful",
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