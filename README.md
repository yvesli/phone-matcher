# phone-matcher

> Simple matcher app that matches phone number with name

## Get JAR file
In terminal
`curl -o phone-number-matcher.jar https://github.com/yvesli/phone-matcher/tree/master/out/artifacts/phone_number_matcher_jar`

## How to use
In Bash terminal `java -jar phone-number-matcher.jar ${name csv file} ${phone csv file} ${output path(either a target csv file or directory to store output)}`
- Console app takes in two files and produce match results
- name.csv in the format of id, name...
- phone.csv in the format of id, phone, name_id

## Performance
- High performance when file size smaller than 2GB but memory consuming
- Low performance when file size larger than 2GB but low in memory consuming

## Documentation
- Go to <a href="https://github.com/yvesli/phone-matcher/blob/master/docs/index.html">Java Docs</a>
