#!/usr/bin/groovy

//Extract category info:
//go through each page, find a [[Category:TITLE]] link
//add page title to category name
//finally, write each category as a set of links into a new page named Category:TITLE.mediawiki

def file=args[0]
//def title=args[1]

def mw = new groovy.xml.Namespace('http://www.mediawiki.org/xml/export-0.4/')
def x = new XmlParser().parse(file)

def cats=[:]
x.page.each{p->
    def title= p[mw.title].text()
    def txt=p[mw.revision][0][mw.text].text()
    def m = (txt=~"(?s).*\\[\\[:?Category:(.+?)\\]\\].*")
    if(m.matches()){
        m.each{all,cat->
            //println "${title}: Category: ${cat}"
            if(!cats[cat]){ cats[cat]=[]}
            cats[cat]<<title
        }
    }else{
        //println "no match: ${title}"
    }
   
}
println "Categories: ${cats}"

cats.each{k,l->
    def cf= new File("Category:${k.trim()}.mediawiki")
    def txt
    if(cf.exists()){
     txt= cf.text
    }
    
    //append to the file
    cf.withWriter{w->
        if(txt){
            w<<txt
            w<<"\n\n----\n\nPages in this category:\n\n"
        }
        l.sort().each{
            w<<"* [[${it}]]\n"
        }
    }
    
}