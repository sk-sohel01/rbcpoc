# tests/test_pipeline.py
import pytest
import scripts.pipeline_lib as pl

def test_acquire_lock_blocks_duplicates(tmp_path):
    job_id = "locktest"
    lockfile = tmp_path / f"pipeline-{job_id}.lock"
    lockfile.write_text("locked")

    with pytest.raises(SystemExit):
        pl.acquire_lock(job_id)

def test_step_skips_if_done():
    state = {"step1": {"status": "done"}}
    output = pl.run_step("step1", state.copy(), exec_fn=lambda s: None)
    assert output["step1"]["status"] == "done"

def test_step_fails_on_missing_deps():
    state = {"step1": {"status": "done"}}
    with pytest.raises(ValueError):
        pl.run_step("step3", state.copy(), deps=["step2"], exec_fn=lambda s: None)

def test_step_runs_if_deps_satisfied():
    state = {
        "step2": {"status": "done"},
        "step3": {"status": "done"}
    }
    called = []

    def dummy_exec(step):
        called.append(step)

    updated = pl.run_step("step4", state.copy(), deps=["step2", "step3"], exec_fn=dummy_exec)
    assert "step4" in updated
    assert called == ["step4"]

