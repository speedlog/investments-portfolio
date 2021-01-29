Feature: Calculate wallet by purchases and disposals
  Depending on purchases and disposals calculate wallet state.

  Scenario: Purchase and disposal unit number are equal - disposal with profit
    Given buy 4 units, 5 per unit with 1 commision
    And sell 4 units, 15 per unit with 0.5 commision
    When calculate wallet records
    Then there should be 1 wallet record
    And first wallet record with 4 units
    And 20 purchase value
    And 60 disposal value
    And 1.5 summary commision
    And 38.5 (202.6315789473684210526315789473684%) profit
    And 739.6052631578947368421052631578947% profit per year
    And there should not be unsold records

  Scenario: Disposal fewer units than in purchase - disposal with profit
    Given buy 4 units, 5 per unit with 1 commision
    And sell 1 units, 15 per unit with 0.5 commision
    When calculate wallet records with today price 10
    And there should be 1 wallet record
    And first wallet record with 1 unit
    And 5 purchase value
    And 15 disposal value
    And 0.75 summary commision
    And 9.25 (194.7368421052631578947368421052632%) profit
    And 710.7894736842105263157894736842107% profit per year
    And there should be 1 unsold record
    And first unsold wallet record with 3 units
    And 15 purchase value
    And 30 disposal value
    And 0.75 summary commision
    And 15.75 (110.5263157894736842105263157894737%) profit
    And 403.421052631578947368421052631579% profit per year

  Scenario: Purchase and two disposals with the same units number - disposal with profit
    Given buy 4 units, 5 per unit with 1 commision
    And sell 2 units, 10 per unit with 0.5 commision
    And sell 2 units, 20 per unit with 1 commision
    When calculate wallet records
    Then there should be 2 wallet records
    And first wallet record with 2 units
    And 10 purchase value
    And 20 disposal value
    And 1 summary commision
    And 9 (94.73684210526315789473684210526316%) profit
    And 345.7894736842105263157894736842105% profit per year
    And second wallet record with 2 units
    And 10 purchase value
    And 40 disposal value
    And 1.5 summary commision
    And 28.5 (300%) profit
    And 1095% profit per year
    And there should not be unsold records

  Scenario: Disposal more units than in one purchase - disposal with profit
    Given buy 4 units, 5 per unit with 1 commision
    And buy 5 units, 7 per unit with 1 commision
    And sell 6 units, 15 per unit with 0.5 commision
    When calculate wallet records with today price 10
    Then there should be 2 wallet records
    And first wallet record with 4 units
    And 20 purchase value
    And 60 disposal value
    And 1.33333333333333333333333333333333335 summary commision
    And 38.66666666666666666666666666666666665 (203.5087719298245614035087719298246%) profit
    And 742.8070175438596491228070175438598% profit per year
    And second wallet record with 2 units
    And 14 purchase value
    And 30 disposal value
    And 0.56666666666666666666666666666666665 summary commision
    And 15.43333333333333333333333333333333335 (113.4803921568627450980392156862745%) profit
    And 414.2034313725490196078431372549019% profit per year
    And there should be 1 unsold record
    And first unsold wallet record with 3 units
    And 21 purchase value
    And 30 disposal value
    And 0.6 summary commision
    And 9.6 (47.05882352941176470588235294117647%) profit
    And 171.7647058823529411764705882352941% profit per year