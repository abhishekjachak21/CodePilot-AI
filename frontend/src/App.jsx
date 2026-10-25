import {useState} from 'react'
import {Code2,Sparkles,ShieldCheck,AlertTriangle,Copy,LoaderCircle} from 'lucide-react'

const sample = `public class PaymentService {
    private String password = "admin123";

    public void processPayment(String customer) {
        System.out.println("Processing payment for " + customer);
        try {
            // payment processing
        } catch (Exception e) {
        }
    }
}`

export default function App(){
  const [code,setCode]=useState(sample),[data,setData]=useState(null),[loading,setLoading]=useState(false),[error,setError]=useState('')
  async function review(){
    setLoading(true);setError('');setData(null)
    try{
      const r=await fetch('/api/code-review',{method:'POST',headers:{'Content-Type':'application/json'},body:JSON.stringify({language:'java',code})})
      if(!r.ok) throw new Error(await r.text()||'Review request failed')
      setData(await r.json())
    }catch(e){setError(e.message||'Cannot connect to backend')}
    finally{setLoading(false)}
  }
  async function copy(value){await navigator.clipboard.writeText(value||'')}
  return <div className="shell">
    <header><div className="brand"><span className="brandIcon"><Code2/></span>CodePilot<span className="accent">.AI</span></div><div className="local"><span/> LOCAL-FIRST REVIEW</div></header>
    <section className="hero"><div className="eyebrow"><Sparkles size={14}/> INTELLIGENT JAVA CODE REVIEW</div><h1>Ship cleaner code.<br/><em>With an AI copilot.</em></h1><p>Review a Java snippet with deterministic checks and optional local AI explanations. You stay in control of every change.</p></section>
    <main className="layout">
      <section className="card editor"><div className="sectionHead"><strong><Code2 size={17}/> Java snippet</strong><span>JAVA</span></div><div className="filename">PaymentService.java</div><textarea value={code} onChange={e=>setCode(e.target.value)} spellCheck="false"/><div className="hint"><ShieldCheck size={14}/> Source is processed by your local backend</div><button className="primary" disabled={loading||!code.trim()} onClick={review}>{loading?<><LoaderCircle className="spin"/> Reviewing...</>:<><Sparkles size={17}/> Review code</>}</button>{error&&<div className="error"><AlertTriangle size={15}/>{error}</div>}<div className="smallNote">Sample includes a credential-like value, console output and an empty catch block.</div></section>
      <section className="results">
        {!data&&!loading&&<div className="card placeholder"><div className="placeholderIcon"><Sparkles/></div><h2>Your review appears here</h2><p>Submit Java code to see findings, severity, explanations and suggested improvements.</p><div className="steps"><span>01&nbsp; Paste</span><span>02&nbsp; Review</span><span>03&nbsp; Improve</span></div></div>}
        {loading&&<div className="card placeholder"><LoaderCircle className="spin"/><h2>Review in progress</h2><p>Running supported rules and requesting local AI context.</p></div>}
        {data&&<><div className="resultHead"><div><div className="eyebrow">ANALYSIS COMPLETE</div><h2>Review results</h2></div><span className={data.aiStatus==='CONNECTED'?'connected':'offline'}>{data.aiStatus==='CONNECTED'?'AI connected':'Rules-only mode'}</span></div><div className="card summary"><b>{data.findings.length}</b><span>Total findings</span><p>{data.summary}</p></div>
        {data.aiExplanation&&<div className="card ai"><strong><Sparkles size={15}/> AI reviewer insight</strong><p>{data.aiExplanation}</p></div>}
        <h3>Findings <small>{data.findings.length}</small></h3>
        {data.findings.length===0&&<div className="card clean">No supported rule patterns detected. This does not guarantee defect-free code.</div>}
        {data.findings.map((f,i)=><article className="card finding" key={f.id+i}><div className="findingMeta"><span>{f.category}</span><b className={f.severity.toLowerCase()}>{f.severity}</b></div><h4>{f.title}</h4><div className="line">Line {f.line} · {f.id}</div><pre>{f.problematicCode}</pre><p>{f.explanation}</p><div className="fix"><b>Suggested fix</b><p>{f.suggestedFix}</p></div><div className="corrected"><div><b>Example improvement</b><button onClick={()=>copy(f.correctedCode)}><Copy size={13}/> Copy fix</button></div><pre>{f.correctedCode}</pre></div><div className="source">DETERMINISTIC RULE · VERIFY BEFORE APPLYING</div></article>)}
        <div className="disclaimer"><AlertTriangle size={14}/> Heuristic findings may be incomplete or incorrect. This MVP is not a compiler or complete SAST tool.</div></>}
      </section>
    </main>
    <footer>CODEPILOT.AI · MVP <span>Developer-controlled code quality</span></footer>
  </div>
}
