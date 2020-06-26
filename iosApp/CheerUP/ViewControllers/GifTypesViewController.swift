//
//  ViewController.swift
//  CheerUP
//
//  Created by eppetrova on 12/04/2020.
//  Copyright © 2020 eppetrova. All rights reserved.
//

import UIKit
import SharedCode

class GifTypesViewController: UIViewController, UICollectionViewDataSource, UICollectionViewDelegate, UICollectionViewDelegateFlowLayout {
    @IBOutlet var collectionView: UICollectionView!
    
    private let gifService = SharedCode.GifService()
    private var selectedGifTypes = Set<GifType>()


    override func viewDidLoad() {
        super.viewDidLoad()
        self.title = "Cheer up!"
    }
    
    // MARK: UICollectionViewDataSourse, UICollectionViewDelegate
    
    func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return gifService.availableTypes.count
     }
     
     func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "BabySelectCell", for: indexPath) as! BabySelectCollectionViewCell
        let babyType = gifService.availableTypes[indexPath.row]
        cell.titleLabel.text = babyType.title
        cell.contentView.backgroundColor = UIColor(red: CGFloat(babyType.color.red) / 255.0, green: CGFloat(babyType.color.green) / 255.0 , blue: CGFloat(babyType.color.blue) / 255.0, alpha: 1.0)
        cell.checkmarkIcon.isHidden = !selectedGifTypes.contains(babyType)
        return cell
     }
    
    func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        let babyType = gifService.availableTypes[indexPath.row]
        if selectedGifTypes.contains(babyType) {
          selectedGifTypes.remove(babyType)
        } else {
          selectedGifTypes.insert(babyType)
        }
        collectionView.reloadItems(at: [indexPath])
    }
    
    func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let side = (collectionView.bounds.width - 10.0) / 2
        return CGSize(width: side, height: side)
    }
    
    // MARK: Navigation
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if let vc = segue.destination as? GifsViewController {
            vc.gifService = gifService
            vc.selectedGifTypes = Array(selectedGifTypes)
        }
    }
}



