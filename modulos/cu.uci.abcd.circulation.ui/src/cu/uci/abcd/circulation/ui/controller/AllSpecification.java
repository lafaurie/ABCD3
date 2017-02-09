package cu.uci.abcd.circulation.ui.controller;

public class AllSpecification {

//FIXME ELIMINAR COMENTARIOS 
 /*   public Specification<Person> searchPersonDB(final String firstName, final String DNI,final String firstSurname) {

        return new Specification<Person>() {

            @Override
            public Predicate toPredicate(Root<Person> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();

                if (firstName != null) {
                    predicateList.add(criteriaBuilder.or(criteriaBuilder.like(root.<String> get("firstName"), "%" + firstName + "%")));
                }
                
                 if(DNI!= null){
                 predicateList.add(criteriaBuilder.or(criteriaBuilder.like(root.<String>get("DNI"), "%"+DNI+"%")));
                 }
                
                 if(firstSurname!= null) {
                 predicateList.add(criteriaBuilder.or(criteriaBuilder.like(root.<String>get("firstSurname"),"%"+firstSurname+"%")));
                }
              
                return criteriaBuilder.or(predicateList.toArray(new Predicate[predicateList.size()]));
           }
        };
    }*/
  /*  public static Specification<LoanUser> searchLoanUserFragment(final String firstName,final String DNI,final String loan_user_code) {

        return new Specification<LoanUser>() {

            @Override
            public Predicate toPredicate(Root<LoanUser> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
               
                Path<Person> person = root.get("person"); 
               // Path<Nomenclator> loanUserState = root.get("loanUserState");  
                            
              // predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(loanUserState.<Long> get("nomenclatorID"), Nomenclator.LOANUSER_STATE_ACTIVE)));
                
                if (firstName != null) {
                    predicateList.add(criteriaBuilder.or(criteriaBuilder.like(person.<String> get("firstName"), "%" + firstName + "%")));
                   
                }
                if (DNI != null) {
                    predicateList.add(criteriaBuilder.or(criteriaBuilder.like(person.<String> get("DNI"), "%" + DNI + "%")));
                }
                if (loan_user_code != null) {
                    predicateList.add(criteriaBuilder.or(criteriaBuilder.like(root.<String> get("loanUserCode"), "%" + loan_user_code + "%")));
                 }
              
                
                
                return criteriaBuilder.or(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }*/

   /* public static Specification<LoanUser> searchLoanUser(final String firstName,final String DNI,final Room room_user,final String loan_user_code, final Nomenclator loan_user_type_id, final Nomenclator loan_user_state) {

        return new Specification<LoanUser>() {

            @Override
            public Predicate toPredicate(Root<LoanUser> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
               
                Path<Person> person = root.get("person"); 
                Path<Room> room = root.get("registeredatroom");
                Path<Nomenclator> loanUserType = root.get("loanUserType");
                Path<Nomenclator> loanUserState = root.get("loanUserState");

                if (firstName != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.like(person.<String> get("firstName"), "%" + firstName + "%")));
                }
                if (DNI != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.like(person.<String> get("DNI"), "%" + DNI + "%")));
                }
                if (room_user != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(room, room_user)));
                }
                if (loan_user_code != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.like(root.<String> get("loanUserCode"), "%" + loan_user_code + "%")));
                }
                if (loan_user_type_id != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(loanUserType, loan_user_type_id)));
                }
                if (loan_user_state != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(loanUserState, loan_user_state)));
                }

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }*/
//title, author, record_type_id, loan_object_state, inventory_number, control_number
  /*  public Specification<LoanObject> searchLoanObject(final String title,final String author, final Nomenclator record_type_id, final Nomenclator loan_object_state, final String inventory_number,final Long control_number) {

        return new Specification<LoanObject>() {

            @Override
            public Predicate toPredicate(Root<LoanObject> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicateList = new ArrayList<Predicate>();

                Path<Nomenclator> recordtype = root.get("recordType");
                Path<Nomenclator> loanObjectState = root.get("loanObjectState");

             //  Path<Date> registrationDate = root.get("registrationDate");

                if (title != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.like(root.<String> get("title"), "%" + title + "%")));
                }
                if (author != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.like(root.<String> get("author"), "%" + author + "%")));
                }
                        
                if (inventory_number != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.like(root.<String> get("inventorynumber"), "%" + inventory_number + "%")));
                }
                if (control_number != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(root.<Long> get("controlNumber"), control_number)));
                }

                if (record_type_id != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(recordtype, record_type_id)));
                }
                if (loan_object_state != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(loanObjectState, loan_object_state)));
                }
              if (startFrom != null && startUp!= null ) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.between(registrationDate,startFrom,startUp)));
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }
    public Specification<LoanObject> searchLoanObjectInventoryNumber(final String inventory_number) {

        return new Specification<LoanObject>() {

            @Override
            public Predicate toPredicate(Root<LoanObject> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicateList = new ArrayList<Predicate>();

                Path<Nomenclator> loanObjectState = root.get("loanObjectState"); 
                
                predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(loanObjectState.<Long> get("nomenclatorID"), Nomenclator.LOANOBJECT_STATE_AVAILABLE)));
                
                if (inventory_number != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.like(root.<String> get("inventorynumber"), "%" + inventory_number + "%")));
              }
              
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }
    public Specification<Penalty> searchPenaltyConsult(final Nomenclator penalty_type, final Nomenclator penalty_state, final Nomenclator loan_user_type_id, final String loan_user_code) {

        return new Specification<Penalty>() {

            @Override
            public Predicate toPredicate(Root<Penalty> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

                List<Predicate> predicateList = new ArrayList<Predicate>();

                Path<Nomenclator> penaltyType = root.get("penaltyType");
                Path<Nomenclator> penaltyState = root.get("penaltyState");
                Path<LoanUser> loanUser = root.get("loanUser");
                Path<Nomenclator> loanUserType = loanUser.get("loanUserType");

                if (penalty_type != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(penaltyType, penalty_type)));
                }
                if (penalty_state != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(penaltyState, penalty_state)));
                }
                if (loan_user_type_id != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(loanUserType, loan_user_type_id)));
                }
                if (loan_user_code != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.like(loanUser.<String> get("loanUserCode"), "%" + loan_user_code + "%")));
                }

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }*/

   /* public Specification<Reservation> searchReservationConsult(final String loan_user, final String loan_user_code, final Nomenclator loan_user_type_id, final String title, final Long control_number, final Nomenclator reservation_state) {

        return new Specification<Reservation>()
        {
            @Override
            public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
               
                Path<LoanUser> buscarUser = root.get("loanUser");   
                Path<Person> buscarPerson=buscarUser.get("person");
                Path<User> buscarUsuario=buscarPerson.get("user");
              
                Path<LoanObject> buscarObject = root.get("loanObject");
                Path<Nomenclator> reservationState = root.get("state");
                Path<Nomenclator> loanUserType = buscarUser.get("loanUserType");

               if (loan_user != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.like(buscarUsuario.<String> get("username"), "%" + loan_user + "%")));

                }
                if (loan_user_code != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.like(buscarUser.<String> get("loanUserCode"), "%" + loan_user_code + "%")));
                }
                if (loan_user_type_id != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(loanUserType, loan_user_type_id)));
                }
                if (title != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.like(buscarObject.<String> get("title"), "%" + title + "%")));
                }
                if (control_number != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarObject.<Long> get("controlNumber"), control_number)));
                }

                if (reservation_state != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(reservationState, reservation_state)));
                }

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };
    }*/

    
  /*  public Specification<Transaction> searchTransaction(final Long control_number,final String title,final String loan_user_code, final Nomenclator loan_user_type_id,final Nomenclator record_type_id,final Nomenclator transaction_state,final Nomenclator loan_type)
    {
    return new Specification<Transaction>()
    {

            @Override
            public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                    List<Predicate> predicateList = new ArrayList<Predicate>();
     
                    Path<Nomenclator> loanType = root.get("loanType");
                    Path<Nomenclator> state = root.get("state");
                    
                    Path<LoanUser> buscarUser = root.get("loanUser");                    
                    Path<LoanObject> buscarObject = root.get("loanObject");
                    Path<Nomenclator> loanUserType = buscarUser.get("loanUserType");
                    Path<Nomenclator> recordType = buscarObject.get("recordType");
                   
                  
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(
                                    root.<Boolean> get("isparent"), false)));
                    
                    if (control_number != null) {
                        predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarObject.<Long> get("controlNumber"), control_number)));
                    }
                    if (title!= null) 
                    {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.like(buscarObject.<String> get("title"), "%" + title + "%")));
                    } 
                    if (loan_user_code!= null) {
                        predicateList.add(criteriaBuilder.and(criteriaBuilder.like(buscarUser.<String> get("loanUserCode"), "%" + loan_user_code + "%")));
                    }
                                      
                 /*   Path<Person> buscarPerson=buscarUser.get("person");
                   Path<User> buscarUsuario=buscarPerson.get("user");
                                     
                     
                    if (loan_user!= null) 
                    {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.like(buscarUsuario.<String> get("username"), "%" + loan_user + "%")));

                    }
                    
                    if (first_Name!= null) 
                    {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.like(buscarPerson.<String> get("firstName"), "%" + first_Name + "%")));
                    }
                    if (second_Name!= null) 
                    {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.like(buscarPerson.<String> get("secondName"), "%" + second_Name + "%")));

                    }                    
                    if (first_Surname!= null) 
                    {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.like(buscarPerson.<String> get("firstSurname"), "%" + first_Surname + "%")));

                    }
                    if (second_Surname!= null) {
                            predicateList.add(criteriaBuilder.and(criteriaBuilder.like(buscarPerson.<String> get("secondSurname"), "%" + second_Surname + "%")));
                    }
                    
                    if (loan_user_type_id != null) {
                            predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(loanUserType, loan_user_type_id)));
                    }
                    if (record_type_id != null) {
                            predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(recordType, record_type_id)));
                    }
                   
                    if (transaction_state != null) {
                        predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(state, transaction_state)));
                    }
                    
                    if (loan_type != null) {
                            predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(loanType, loan_type)));
                    }
                    return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
            };
        }*/
    
   /* public Specification<Transaction> searchTransactionByLoanUserReturn(final Long loan_user_id) {
      return new Specification<Transaction>()
        {
            @Override
            public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();

                Path<LoanUser> buscarUser = root.get("loanUser");
                Path<Nomenclator> buscarTransactionState = root.get("state");  
            
                if (loan_user_id != null) {
                	  predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarUser.<Long> get("id"), loan_user_id)));
                      predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(buscarTransactionState.<Long> get("nomenclatorID"), Nomenclator.LOAN_STATE_RETURN)));
                      predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(buscarTransactionState.<Long> get("nomenclatorID"), Nomenclator.LOAN_STATE_NOT_DELIVERED)));
                      }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }*/
    
   /* public Specification<Transaction> searchTransactionByLoanUserCurrent(final Long loan_user_id) {
        return new Specification<Transaction>()
        {
            @Override
            public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();

                Path<LoanUser> buscarUser = root.get("loanUser");
                Path<Nomenclator> buscarTransactionState = root.get("state");  
            
                if (loan_user_id != null) {
                	  predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarUser.<Long> get("id"), loan_user_id)));
                      predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(buscarTransactionState.<Long> get("nomenclatorID"), Nomenclator.LOAN_STATE_RETURN)));
                      predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(buscarTransactionState.<Long> get("nomenclatorID"), Nomenclator.LOAN_STATE_NOT_DELIVERED)));
                      predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(buscarTransactionState.<Long> get("nomenclatorID"), Nomenclator.LOAN_STATE_DELETE)));
                      }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }
    public Specification<Transaction> searchTransactionByLoanUserHistory(final Long loan_user_id) {

        return new Specification<Transaction>()

        {

            @Override
            public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();

                Path<LoanUser> buscarUser = root.get("loanUser");
                Path<Nomenclator> buscarTransactionState = root.get("state");  
            
                if (loan_user_id != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarUser.<Long> get("id"), loan_user_id)));
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(buscarTransactionState.<Long> get("nomenclatorID"), Nomenclator.LOAN_STATE_BORROWED)));
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(buscarTransactionState.<Long> get("nomenclatorID"), Nomenclator.LOAN_STATE_RENEW)));
                      
                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));
            }
        };
    }*/

   /* public Specification<Reservation> searchReservationLoanUserCurrent(final Long loan_user_id) {

        return new Specification<Reservation>()

        {

            @Override
            public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();

                Path<LoanUser> buscarUser = root.get("loanUser");
                Path<Reservation> state=root.get("state");
                if (loan_user_id != null) 
                {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarUser.<Long> get("id"), loan_user_id)));
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(state.<Long> get("nomenclatorID"), Nomenclator.RESERVATION_STATE_CANCELLED)));
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(state.<Long> get("nomenclatorID"), Nomenclator.RESERVATION_STATE_DELETED)));
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(state.<Long> get("nomenclatorID"), Nomenclator.RESERVATION_STATE_EXECUTED)));
                    

                }

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };
    }
    
    public Specification<Reservation> searchReservationLoanUserHistory(final Long loan_user_id) {

        return new Specification<Reservation>()

        {

            @Override
            public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();

                Path<LoanUser> buscarUser = root.get("loanUser");
                Path<Reservation> state=root.get("state");
                if (loan_user_id != null) 
                {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarUser.<Long> get("id"), loan_user_id)));
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(state.<Long> get("nomenclatorID"), Nomenclator.RESERVATION_STATE_PENDING)));
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(state.<Long> get("nomenclatorID"), Nomenclator.RESERVATION_STATE_VALID)));
                    

                }
                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };
    }*/

   /* public Specification<Penalty> searchPenaltyByLoanUser(final Long loan_user_id) {

        return new Specification<Penalty>()

        {

            @Override
            public Predicate toPredicate(Root<Penalty> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();

                Path<LoanUser> buscarUser = root.get("loanUser");

                if (loan_user_id != null) {

                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarUser.<Long> get("id"), loan_user_id)));

                }

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };
    }*/

    // /

   /* public Specification<Transaction> searchTransactionByLoanObject(final Long loan_object_id) {

        return new Specification<Transaction>()

        {

            @Override
            public Predicate toPredicate(Root<Transaction> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();

                Path<LoanObject> buscarObject = root.get("loanObject");

                if (loan_object_id != null) {

                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarObject.<Long> get("loanObjectID"), loan_object_id)));

                }

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };
    }*/

  /*  public Specification<Reservation> searchReservationLoanObject(final Long loan_object_id) {

        return new Specification<Reservation>()

        {

            @Override
            public Predicate toPredicate(Root<Reservation> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();

                Path<LoanObject> buscarObject = root.get("loanObject");

                if (loan_object_id != null) {

                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarObject.<Long> get("loanObjectID"), loan_object_id)));

                }

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };
    }
*/
   /* public Specification<Penalty> searchPenaltyByLoanObject(final Long loan_object_id) {

        return new Specification<Penalty>()

        {

            @Override
            public Predicate toPredicate(Root<Penalty> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();

                Path<LoanObject> buscarObject = root.get("loanObject");

                if (loan_object_id != null) {

                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarObject.<Long> get("loanObjectID"), loan_object_id)));

                }

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };
    }

    public Specification<Penalty> searchPenaltyCurrent(final Long loan_user_id) {

        return new Specification<Penalty>()

        {

            @Override
            public Predicate toPredicate(Root<Penalty> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();

                Path<LoanUser> buscarUser = root.get("loanUser");
                Path<Penalty> statePenalty = root.get("penaltyState");
                

                if (loan_user_id != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarUser.<Long> get("id"), loan_user_id)));
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(statePenalty.<Long> get("nomenclatorID"), Nomenclator.PENALTY_STATE_ACTIVE_PAID)));
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(statePenalty.<Long> get("nomenclatorID"), Nomenclator.PENALTY_STATE_DELETED)));
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(statePenalty.<Long> get("nomenclatorID"), Nomenclator.PENALTY_STATE_INACTIVE)));
                }

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };
    }
    public Specification<Penalty> searchPenaltyHistory(final Long loan_user_id) {

        return new Specification<Penalty>()

        {

            @Override
            public Predicate toPredicate(Root<Penalty> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicateList = new ArrayList<Predicate>();

                Path<LoanUser> buscarUser = root.get("loanUser");
                Path<Penalty> statePenalty = root.get("penaltyState");
                

                if (loan_user_id != null) {
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.equal(buscarUser.<Long> get("id"), loan_user_id)));
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(statePenalty.<Long> get("nomenclatorID"), Nomenclator.PENALTY_STATE_ACTIVE_PENDING_PAYMENT)));
                    predicateList.add(criteriaBuilder.and(criteriaBuilder.notEqual(statePenalty.<Long> get("nomenclatorID"), Nomenclator.PENALTY_STATE_ACTIVE)));
                }

                return criteriaBuilder.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };
    }*/

}

