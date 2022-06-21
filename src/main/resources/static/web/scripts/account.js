Vue.createApp({
    
    data() {
      return {
          URLCLIENT: "/api/clients/current",
          client:[],
          intials:"",
          accounts:[],
          account:[],
          transactions:[],
          transaction:[],
          debitClass: 'table-danger',
          creditClass: 'table-success',

        }
      },
  
    created(){
        axios.get(this.URLCLIENT)
        .then (data => {
            this.client = data.data;
            let firstNameLetter = this.client.firstName.charAt(0).toUpperCase()
            let secondNameLetter = this.client.lastName.charAt(0).toUpperCase()
            this.intials =  firstNameLetter + secondNameLetter
        })
        .catch(error => console.warn(error.message));

        const urlParams = new URLSearchParams(window.location.search);
        const paramId = urlParams.get('id');

        axios.get(`/api/clients/current/accounts/${paramId}`)
        .then (data => {
            this.transactions = data.data.transactions.sort((a,b) => b.id - a.id)
        }).catch(error => console.warn(error.message));

    },
  
    methods: {
      logOut(){
        axios.post('/api/logout')
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
        header.classList.toggle('body-pd')
        }
    },
  

    computed: { 
    },
    
  }).mount('#app')