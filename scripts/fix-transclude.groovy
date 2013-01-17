#!/usr/bin/groovy

//Fix transclusion of pages, by replacing them with links
//go through each page, find a {{:PageName}} link
//and remove the : from the beginning, replace {{}} with [[]]
//finally, write the contents back to the file

def dir=new File(args[0])
//def title=args[1]

// def mw = new groovy.xml.Namespace('http://www.mediawiki.org/xml/export-0.4/')
// def x = new XmlParser().parse(file)

dir.eachFileRecurse(groovy.io.FileType.FILES){pfile->
    if(!(pfile.name==~/^.*\.mediawiki$/)){
        return
    }
    // def dir=new File(".")
    // def title= p[mw.title].text()
    // def pfile=new File(dir,title+".mediawiki")
    // pfile = new File(pfile.absolutePath)
    
    def orig=pfile.text
    txt=orig.replaceAll("(?s)\\{\\{:?(.+?)\\}\\}"){m->
        def v=m[1]
        def mt = v=~/^[Mm]ain\|:?(.+)$/
        if(v in ['wikify']){
            ''
        }else if(mt.matches() && mt.size()>0){
            '[['+mt[0][1].trim()+']]'
        }else{
            '[['+v+']]'
        }
    }
    if(txt!=orig){
        println "Discovered ${pfile} has transclusion"
        pfile.withWriter{w->
            w<<txt
        }
    }else{
        
    }
   
}
