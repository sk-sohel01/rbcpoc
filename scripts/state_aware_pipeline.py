# scripts/state_aware_pipeline.py

import json
import os
import time
import subprocess

STATE_FILE = "state/state.json"
LOCK_FILE = "/tmp/pipeline.lock"

def load_state():
    if not os.path.exists(STATE_FILE):
        return {}
    with open(STATE_FILE) as f:
        return json.load(f)

def save_state(state):
    with open(STATE_FILE, "w") as f:
        json.dump(state, f, indent=2)

def check_lock():
    if os.path.exists(LOCK_FILE):
        raise RuntimeError("Another execution is in progress.")
    with open(LOCK_FILE, "w") as f:
        f.write(str(time.time()))

def release_lock():
    if os.path.exists(LOCK_FILE):
        os.remove(LOCK_FILE)

def run_step(step_name, script_path, depends_on=[], required_vars=[]):
    state = load_state()

    # 🟡 Skip if this step is already completed
    if state.get(step_name, {}).get("status") == "done":
        print(f"✅ {step_name} already completed. Skipping.")
        return

    for dep in depends_on:
        if state.get(dep, {}).get("status") != "done":
            print(f"⛔ Skipping {step_name}: depends on {dep}, which is not done.")
            return

    for var in required_vars:
        if var not in state:
            print(f"⛔ Missing required variable: {var} for step {step_name}")
            return

    print(f"🚀 Running {step_name}...")
    subprocess.run(["bash", script_path], check=True)
    state[step_name] = {
        "status": "done",
        "output": f"result_of_{step_name}"
    }
    save_state(state)
    print(f"✅ {step_name} completed.\n")

def main():
    try:
        check_lock()
        run_step("step1", "steps/step1.sh")
        run_step("step2", "steps/step2.sh", depends_on=["step1"])
        run_step("step3", "steps/step3.sh", depends_on=["step2"])
        run_step("step4", "steps/step4.sh", depends_on=["step2", "step3"], required_vars=["step1"])
    except Exception as e:
        print(f"Pipeline failed: {e}")
    finally:
        release_lock()

if __name__ == "__main__":
    main()

