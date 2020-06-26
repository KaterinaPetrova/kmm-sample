//
//  UIColor+Utils.swift
//  CheerUP
//
//  Created by eppetrova on 18.04.2020.
//  Copyright © 2020 eppetrova. All rights reserved.
//

import Foundation
import UIKit

extension UIColor {
    public convenience init?(int: Int, alpha: CGFloat = 1.0) {
        let r = CGFloat((int & 0xff0000) >> 16) / 255
        let g = CGFloat((int & 0x00ff00) >> 8) / 255
        let b = CGFloat((int & 0x0000ff) >> 0) / 255
        self.init(red: r, green: g, blue: b, alpha: alpha)
    }
}
