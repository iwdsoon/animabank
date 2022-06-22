Vue.createApp({
    
    data() {
      return {
          URLAPI: "/api/clients/current",
          client:[],
          intials:"",
          cards:[],
          cardsDebit:[],
          cardsCredit:[],

        }
      },
  
    created(){
        axios.get(this.URLAPI)
        .then (data => {
            this.client = data.data;
            this.cards = data.data.cards.sort((a,b) => a.id - b.id)
            this.cardsDebit = this.cards.filter(card => card.type == "DEBIT")
            this.cardsCredit = this.cards.filter(card => card.type == "CREDIT")
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
        },

        disableCard(id){
          Swal.fire({
            title: 'Disable Card',
            text: "Are you sure you want to disable this card?",
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
                      axios.patch('/api/clients/current/cards', `id=${id}&password=${result.value}`)
                      .then(() => {
                        Swal.fire({
                          title: 'Succeed',
                          text: "Card disabled successful",
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


    },
  

    computed: { 
    },
    
  }).mount('#app')