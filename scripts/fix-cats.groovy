#!/usr/bin/groovy

//Fix category top-links
//go through each page, find a [[:Category:TITLE]] link
//and remove the : from the beginning
//finally, write the contents back to the file

def file=args[0]
//def title=args[1]

def mw = new groovy.xml.Namespace('http://www.mediawiki.org/xml/export-0.4/')
def x = new XmlParser().parse(file)

x.page.each{p->
    def dir=new File(".")
    def title= p[mw.title].text()
    def pfile=new File(dir,title+".mediawiki")
    pfile = new File(pfile.absolutePath)
    
    def orig=pfile.text
    txt=orig.replaceAll("(?s)\\[\\[:Category:"){
        '[[Category:'
    }
    if(txt!=orig){
        println "Discovered ${title} has link"
        pfile.withWriter{w->
            w<<txt
        }
    }else{
        
    }
   
}
