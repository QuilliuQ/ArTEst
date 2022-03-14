package ru.sylas.myapplication

import android.net.Uri
import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.google.ar.core.Anchor
import com.google.ar.core.HitResult
import com.google.ar.core.Plane
import com.google.ar.sceneform.AnchorNode
import com.google.ar.sceneform.rendering.ModelRenderable
import com.google.ar.sceneform.ux.ArFragment
import com.google.ar.sceneform.ux.TransformableNode


class MainActivity : AppCompatActivity() {

    private lateinit var lampPostRenderable : ModelRenderable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val arFragment: ArFragment = supportFragmentManager.findFragmentById(R.id.fragment) as ArFragment

        ModelRenderable.builder()
            .setSource(this, R.raw.andy)
            .build()
            .thenAccept { renderable: ModelRenderable ->
                lampPostRenderable = renderable
            }


        arFragment.setOnTapArPlaneListener { hitresult: HitResult, _: Plane?, _: MotionEvent? ->
            val anchor: Anchor = hitresult.createAnchor()
            val anchorNode = AnchorNode(anchor)
            anchorNode.setParent(arFragment.arSceneView.scene)
            val lamp =
                TransformableNode(arFragment.transformationSystem)
            lamp.setParent(anchorNode)
            lamp.renderable = lampPostRenderable
            lamp.select()
        }
    }
}