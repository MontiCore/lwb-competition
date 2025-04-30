
### Main Sources

```
cloc-2.04.exe --read-lang-def=my_definitions.txt src/main
      29 text files.
      29 unique files.
       0 files ignored.

github.com/AlDanial/cloc v 2.04  T=0.12 s (245.4 files/s, 9087.4 lines/s)
---------------------------------------------------------------------------------
Language                       files          blank        comment           code
---------------------------------------------------------------------------------
Java                              17            162             70            635
Freemarker Template               10             16              3            143
MontiCore Grammar                  2             13              4             28
---------------------------------------------------------------------------------
SUM:                              29            191             77            806
---------------------------------------------------------------------------------

```

### Including Tests, Example Models, and Gradle SetUp

```
cloc-2.04.exe --read-lang-def=my_definitions.txt 85cfe83f96c25709de6f34320af9ff532674ba89
      42 text files.
      41 unique files.
       3 files ignored.

github.com/AlDanial/cloc v 2.04  T=0.39 s (104.7 files/s, 3662.0 lines/s)
---------------------------------------------------------------------------------
Language                       files          blank        comment           code
---------------------------------------------------------------------------------
Java                              20            181             70            752
Freemarker Template               10             16              3            143
Gradle                             2             12              6             67
Markdown                           1              8              0             38
MontiCore Grammar                  2             13              4             28
QLS Model                          1              4              4             27
Text                               1              0              0             24
Questionnaire Model                2              1              0             20
Properties                         2              2              1             10
---------------------------------------------------------------------------------
SUM:                              41            237             88           1109
---------------------------------------------------------------------------------

```
