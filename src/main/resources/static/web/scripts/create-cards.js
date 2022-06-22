Vue.createApp({
    
    data() {
      return {
          URLAPI: "/api/clients/current",
          client:[],
          intials:"",
          cards:[],
          cardsDebit:[],
          cardsCredit:[],
          cardType:"",
          cardColor:"",
          errorActive: false,
          errorMessage: null
        }
      },
  
    created(){
        axios.get(this.URLAPI)
        .then (data => {
            this.client = data.data;
            this.cards = data.data.cards
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

      createCard(){
        axios.post('/api/clients/current/cards',`cardType=${this.cardType}&cardColor=${this.cardColor}`,
        {headers:
            {'content-type':'application/x-www-form-urlencoded'}}
            ).then(() => window.location.replace("/web/cards.html")
            ).catch(error =>{
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
        header.classList.toggle('header-pd')
        }

    },
  

    computed: { 
    },
    
  }).mount('#app')