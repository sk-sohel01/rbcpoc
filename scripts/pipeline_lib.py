# scripts/pipeline_lib.py
import json
import os
import sys

def get_state_file(job_id):
    return f"state/state-{job_id}.json"

def load_state(job_id):
    path = get_state_file(job_id)
    if os.path.exists(path):
        with open(path, 'r') as f:
            return json.load(f)
    return {}

def save_state(state, job_id):
    with open(get_state_file(job_id), 'w') as f:
        json.dump(state, f, indent=2)

def acquire_lock(job_id):
    lockfile = f"/tmp/pipeline-{job_id}.lock"
    if os.path.exists(lockfile):
        print(f"❌ Another pipeline with JOB_ID '{job_id}' is already running.")
        sys.exit(1)
    open(lockfile, 'w').write('locked')
    return lockfile

def release_lock(lockfile):
    if os.path.exists(lockfile):
        os.remove(lockfile)

def run_step(step, state, deps=[], exec_fn=None):
    for dep in deps:
        if dep not in state or state[dep]['status'] != 'done':
            raise ValueError(f"🚫 Dependency '{dep}' not met for step '{step}'")

    if step in state and state[step]['status'] == 'done':
        print(f"✅ {step} already completed. Skipping.")
        return state

    print(f"🚀 Running {step}...")
    if exec_fn:
        exec_fn(step)
    state[step] = {"status": "done", "output": f"output_of_{step}"}
    return state

