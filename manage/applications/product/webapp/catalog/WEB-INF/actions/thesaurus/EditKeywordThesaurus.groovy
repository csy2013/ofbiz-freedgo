import org.ofbiz.entity.condition.EntityCondition

relationshipEnums = delegator.findList("Enumeration", EntityCondition.makeCondition([enumTypeId : 'KW_THES_REL']), null, ['sequenceId'], null, true)

keywordThesauruses = delegator.findList("KeywordThesaurus", null, null, ['enteredKeyword'], null, false)

//if no param sent in make firstLetter 'a' else use firstLetter passed in
firstLetterString = request.getParameter("firstLetter")
if (!firstLetterString) {
    firstLetter = 'a'
}
else {
    firstLetter = firstLetterString.charAt(0)
}

//add elememts to new list as long as it is smaller then 20,
//  but always get all of the first letter
keywordThesaurusIter = keywordThesauruses.iterator()
newKeywordThesaurus = []
specialCharKeywordThesaurus = []
currentLetter = firstLetter
if (keywordThesaurusIter) {
    while (keywordThesaurusIter) {
        keywordThesaurus = keywordThesaurusIter.next()
        if (keywordThesaurus.get("enteredKeyword").charAt(0)<'a' ||
                keywordThesaurus.get("enteredKeyword").charAt(0)>'z') {
            specialCharKeywordThesaurus.add(keywordThesaurus)
        } else if (keywordThesaurus.get("enteredKeyword").charAt(0) >= firstLetter) {
            if (keywordThesaurus.get("enteredKeyword").charAt(0) == currentLetter ||
                    newKeywordThesaurus.size()<20) {
                newKeywordThesaurus.add(keywordThesaurus)
                currentLetter = keywordThesaurus.get("enteredKeyword").charAt(0)
            }
        }
    }
}
if ((specialCharKeywordThesaurus.size() > 0 && newKeywordThesaurus.size()<20) || firstLetter=='z') {
    specialCharKeywordThesaurusIter = specialCharKeywordThesaurus.iterator()
    while (specialCharKeywordThesaurusIter) {
        keywordThesaurus = specialCharKeywordThesaurusIter.next()
        newKeywordThesaurus.add(keywordThesaurus)
    }
}

//create list for a-z
letterList = []
for (i='a'; i<='z'; i++) {
    letterList.add(i)
}

context.relationshipEnums = relationshipEnums
context.keywordThesauruses = newKeywordThesaurus
context.firstLetter = firstLetter
context.letterList = letterList
