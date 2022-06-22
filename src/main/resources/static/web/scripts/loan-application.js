let app= Vue.createApp({
    
    data() {
      return {
          URLAPICLIENT: "/api/clients/current",
          URLAPILOANS: "/api/loans",
          client:[],
          intials:"",
          accounts:[],
          clientLoans:[],
          loans:[],
          loanType:0,
          amount:0,
          payments:0,
          accountDestination:"",
          personalForm: false,
          autoForm: false,
          mortgageForm: false,


        }
      },
  
    created(){
        axios.get(this.URLAPICLIENT)
        .then (data => {
            this.client = data.data
            this.accounts = data.data.accounts.sort((a,b) => a.id - b.id)
            this.clientLoans = data.data.loans
            let firstNameLetter = this.client.firstName.charAt(0).toUpperCase()
            let secondNameLetter = this.client.lastName.charAt(0).toUpperCase()
            this.intials =  firstNameLetter + secondNameLetter
        })
        .catch(error => console.warn(error.message));


        axios.get(this.URLAPILOANS)
        .then(data => {
            this.loans = data.data
        })
        .catch(error => console.warn(error.message));

    },
  
    methods: {
      logOut(){
        axios.post('/api/logout')
        .then(() => window.location.replace("/web/login.html"))
        .catch(error => console.log(error))
      },

      showPersonalForm(){
        this.personalForm = true
        this.autoForm = false
        this.mortgageForm = false
      },

      showAutoForm(){
        this.personalForm = false
        this.autoForm = true
        this.mortgageForm = false
      },

      showMortgageForm(){
        this.personalForm = false
        this.autoForm = false
        this.mortgageForm = true
      },

      applyLoan(){

        let payment = parseInt(this.payments)
        let loan = parseInt(this.loanType)

        Swal.fire({
          title: 'Loan',
          text: "Are you sure you want to apply to this loan?",
          icon: 'warning',
          showCancelButton: true,
          confirmButtonColor: '#014377',
          cancelButtonColor: '#ff0000',
          confirmButtonText: 'Apply now'
        }).then ((result) => {
              if (result.isConfirmed) {
                axios.post('/api/loans',{amount:`${this.amount}`,payments:`${payment}`,accountDestination:`${this.accountDestination}`,loanId:`${loan}`},
                {headers:
                {'content-type':'application/json'}}
                ).then(() => {
                  Swal.fire({
                    title: 'Succeed',
                    text: "Loan applied",
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
          header.classList.toggle('header-pd')
          }


    },
  

    computed: { 

    },
    
  }).mount('#app')